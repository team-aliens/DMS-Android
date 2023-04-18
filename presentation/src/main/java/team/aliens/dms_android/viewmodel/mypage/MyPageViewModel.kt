package team.aliens.dms_android.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.mypage.MyPageEntity
import team.aliens.dms_android.feature.mypage.MyPageEvent
import team.aliens.dms_android.feature.mypage.MyPageState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model._common.PointType
import team.aliens.domain._model.point.FetchPointsOutput
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ForbiddenException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.student.FetchMyPageUseCase
import team.aliens.domain.usecase.point.FetchPointsUseCase
import team.aliens.domain.usecase.student.WithdrawUseCase
import team.aliens.domain.usecase.auth.SignOutUseCase
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val fetchMyPageUseCase: FetchMyPageUseCase,
    private val remotePointListUseCase: FetchPointsUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : BaseViewModel<MyPageState, MyPageEvent>() {

    init {
        fetchMyPage()
    }

    override val initialState: MyPageState
        get() = MyPageState.getDefaultInstance()

    private val _myPageViewEffect = MutableEventFlow<Event>()
    internal val myPageViewEffect = _myPageViewEffect.asEventFlow()

    private val _pointViewEffect = MutableEventFlow<Event>()
    internal val pointViewEffect = _pointViewEffect.asEventFlow()

    private val _signOutEvent = MutableEventFlow<Event>()
    internal val signOutEvent = _signOutEvent.asEventFlow()

    private val _withdrawEvent = MutableEventFlow<Event>()
    internal val withdrawEvent = _withdrawEvent.asEventFlow()

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                signOutUseCase()
            }.onSuccess {
                emitSignOutEvent(
                    Event.SignedOut,
                )
            }
        }
    }

    internal fun withdraw() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                withdrawUseCase()
            }
        }
    }

    internal fun fetchMyPage() {
        viewModelScope.launch {
            kotlin.runCatching {
                fetchMyPageUseCase()
            }.onSuccess {
                state.value.myPageEntity.emit(
                    MyPageEntity(
                        gcn = it.gradeClassNumber,
                        bonusPoint = it.bonusPoint,
                        minusPoint = it.minusPoint,
                        name = it.studentName,
                        phrase = it.phrase,
                        schoolName = it.schoolName,
                        profileImageUrl = it.profileImageUrl,
                        sex = it.sex,
                    ),
                )
            }.onFailure {
                when (it) {
                    is NullPointerException -> emitMyPageViewEffect(Event.NullPointException)
                    is UnauthorizedException -> emitMyPageViewEffect(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> emitMyPageViewEffect(Event.CannotConnectException)
                    is TooManyRequestException -> emitMyPageViewEffect(Event.TooManyRequestException)
                    is ServerException -> emitMyPageViewEffect(Event.InternalServerException)
                    else -> emitMyPageViewEffect(Event.UnknownException)
                }
            }
        }
    }

    fun fetchPointList(
        pointType: PointType,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                remotePointListUseCase(pointType)
            }.onSuccess {
                emitPointViewEffect(Event.FetchPointList(it))
            }.onFailure {
                when (it) {
                    is NullPointerException -> emitPointViewEffect(Event.NullPointException)
                    is BadRequestException -> emitPointViewEffect(Event.BadRequestException)
                    is UnauthorizedException -> emitPointViewEffect(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> emitPointViewEffect(Event.CannotConnectException)
                    is TooManyRequestException -> emitPointViewEffect(Event.TooManyRequestException)
                    is ServerException -> emitPointViewEffect(Event.InternalServerException)
                    else -> emitPointViewEffect(Event.UnknownException)
                }
            }
        }
    }

    private fun emitMyPageViewEffect(event: Event) {
        viewModelScope.launch {
            _myPageViewEffect.emit(event)
        }
    }

    private fun emitPointViewEffect(event: Event) {
        viewModelScope.launch {
            _pointViewEffect.emit(event)
        }
    }

    private fun emitSignOutEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _signOutEvent.emit(event)
        }
    }

    override fun reduceEvent(oldState: MyPageState, event: MyPageEvent) {}

    sealed class Event {
        object SignedOut : Event()
        data class FetchPointList(
            val fetchPointsOutput: FetchPointsOutput,
        ) : Event()

        object BadRequestException : Event()
        object NullPointException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}
