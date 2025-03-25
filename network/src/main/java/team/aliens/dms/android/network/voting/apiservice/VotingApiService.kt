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
import java.time.LocalDate
import java.util.UUID

internal interface VotingApiService {

    @GET("/")
    @RequiresAccessToken
    suspend fun fetchAllVoteSearch(): FetchAllVoteSearchResponse

    @GET("/option/{voting-topic-id}")
    @RequiresAccessToken
    suspend fun fetchCheckVotingItem(
        @Path("voting-topic-id") votingTopicId: UUID,
    ): FetchCheckVotingItemResponse

    @POST("/student/{voting-topic-id}")
    @RequiresAccessToken
    suspend fun fetchCreateVotingItem(
        @Path("voting-topic-id") votingTopicId: UUID,
        @Query("selected_id") selectedId: UUID,
    ): Response<Unit>?

    @DELETE("/student/{vote_id}")
    @RequiresAccessToken
    suspend fun fetchDeleteVotingItem(
        @Path("vote_id") voteId: UUID,
    ): Response<Unit>?

    @GET("/candidate-list/{requestDate}")
    @RequiresAccessToken
    suspend fun modelStudentCandidates(
        @Path("requestDate") requestDate: LocalDate,
    )
}
