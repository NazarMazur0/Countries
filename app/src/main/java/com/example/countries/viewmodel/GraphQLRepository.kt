package com.example.countries.viewmodel

import android.util.Log
import com.apollographql.apollo3.api.ApolloResponse
import com.example.countries.apolloClient
import com.example.rocketreserver.AllCountriesQuery
import com.example.rocketreserver.CountryQuery

class GraphQLRepository() {
    suspend fun getAllCountries( ): List<AllCountriesQuery.Country>?  {
        val response : ApolloResponse<AllCountriesQuery.Data>?
        Log.d("GraphQL", "Request sent")
        response= apolloClient.query(AllCountriesQuery()).execute()
        Log.d("GraphQL", "Success ${response.data}")
       return response!!.data?.countries
    }
    suspend fun getCountry(code:String): CountryQuery.Country? {
        val response : ApolloResponse<CountryQuery.Data>?
        Log.d("GraphQL", "Request sent")
        response= apolloClient.query(CountryQuery(code)).execute()
        Log.d("GraphQL", "Success ${response.data}")
        return response.data?.country
    }
}