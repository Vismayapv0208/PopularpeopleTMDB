package com.example.movie.paging

import android.util.Log
import androidx.compose.ui.geometry.isEmpty
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie.model.Person
import com.example.movie.network.MyApiService
import retrofit2.HttpException
import java.io.IOException


class MyPagingSource(private val apiService: MyApiService) : PagingSource<Int, Person>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getPopularPeople(page)

            if (response.isSuccessful) {
                val people = response.body()?.results ?: emptyList()

                // Log the list of people (items) here
                Log.e("API_RESPONSE", "Fetched People: $people") // Log the actual list of items

                LoadResult.Page(
                    data = people,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (people.isEmpty()) null else page + 1
                )
            } else {
                // Log the error response
                Log.e("API_RESPONSE", "API Error: ${response.errorBody()?.string()}")
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            // Log the exception
            Log.e("API_RESPONSE", "IOException: ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // Log the exception
            Log.e("API_RESPONSE", "HttpException: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
