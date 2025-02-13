package com.example.movie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie.model.Person
import com.example.movie.network.MyApiService
import retrofit2.HttpException
import java.io.IOException

class MyPagingSource(private val apiService: MyApiService, private val showToast: (String) -> Unit) : PagingSource<Int, Person>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getPopularPeople(page)

            if (response.isSuccessful) {
                val people = response.body()?.results ?: emptyList()
                LoadResult.Page(
                    data = people,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (people.isEmpty()) null else page + 1
                )
            } else {
                showToast("Error: ${response.message()}")
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            showToast("IOException: ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            showToast("HttpException: ${e.message}")
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
