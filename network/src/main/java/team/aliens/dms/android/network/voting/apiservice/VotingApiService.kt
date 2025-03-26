package team.aliens.dms.android.network.voting.apiservice

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import team.aliens.dms.android.network.voting.model.FetchModelStudentCandidates
import java.time.LocalDate
import java.util.UUID

internal interface VotingApiService {

    @GET("/votes")
    @RequiresAccessToken
    suspend fun fetchAllVoteSearch(): FetchAllVoteSearchResponse

    @GET("/votes/option/{voting-topic-id}")
    @RequiresAccessToken
    suspend fun fetchCheckVotingItem(
        @Path("voting-topic-id") votingTopicId: UUID,
    ): FetchCheckVotingItemResponse

    @POST("/votes/student/{voting-topic-id}")
    @RequiresAccessToken
    suspend fun fetchCreateVotingItem(
        @Path("voting-topic-id") votingTopicId: UUID,
        @Query("selected_id") selectedId: UUID,
    ): Response<Unit>?

    @DELETE("/votes/student/{vote_id}")
    @RequiresAccessToken
    suspend fun fetchDeleteVotingItem(
        @Path("vote_id") voteId: UUID,
    ): Response<Unit>?

    @GET("/votes/candidate-list/{requestDate}")
    @RequiresAccessToken
    suspend fun fetchModelStudentCandidates(
        @Path("requestDate") requestDate: LocalDate,
    ): FetchModelStudentCandidates
}
