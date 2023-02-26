package team.aliens.dms_android.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.mypage.MyPageEvent
import team.aliens.dms_android.feature.mypage.MyPageState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.enums.PointType
import team.aliens.domain.exception.*
import team.aliens.domain.usecase.mypage.RemoteMyPageUseCase
import team.aliens.domain.usecase.mypage.RemotePointUseCase
import team.aliens.domain.usecase.user.SignOutUseCase
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val remoteMyPageUseCase: RemoteMyPageUseCase,
    private val remotePointListUseCase: RemotePointUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<MyPageState, MyPageEvent>() {

    init {
        fetchMyPage()
    }

    override val initialState: MyPageState
        get() = MyPageState.initial()

    private val _myPageViewEffect = MutableEventFlow<Event>()
    var myPageViewEffect = _myPageViewEffect.asEventFlow()

    private val _pointViewEffect = MutableEventFlow<Event>()
    var pointViewEffect = _pointViewEffect.asEventFlow()

    private val _signOutEvent = MutableEventFlow<Event>()
    internal val signOutEvent = _signOutEvent.asEventFlow()

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                signOutUseCase.execute(Unit)
            }.onSuccess {
                emitSignOutEvent(
                    Event.SignedOut,
                )
            }
        }
    }

    internal fun fetchMyPage() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteMyPageUseCase.execute(Unit)
            }.onSuccess {
                state.value.myPageEntity.gcn = it.gcn
                state.value.myPageEntity.bonusPoint = it.bonusPoint
                state.value.myPageEntity.minusPoint = it.minusPoint
                state.value.myPageEntity.name = it.name
                state.value.myPageEntity.phrase = it.phrase
                state.value.myPageEntity.schoolName = it.schoolName
                state.value.myPageEntity.profileImageUrl = it.profileImageUrl
            }.onFailure {
                when (it) {
                    is NullPointerException -> event(Event.NullPointException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    fun fetchPointList(pointType: PointType) {
        viewModelScope.launch {
            kotlin.runCatching {
                remotePointListUseCase.execute(pointType)
            }.onSuccess {
                event2(Event.FetchPointList)
                setState(state.value.copy(totalPoint = it.totalPoint, pointListEntity = it))
            }.onFailure {
                when (it) {
                    is NullPointerException -> event2(Event.NullPointException)
                    is BadRequestException -> event2(Event.BadRequestException)
                    is UnauthorizedException -> event2(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event2(Event.CannotConnectException)
                    is TooManyRequestException -> event2(Event.TooManyRequestException)
                    is ServerException -> event2(Event.InternalServerException)
                    else -> event2(Event.UnknownException)
                }
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _myPageViewEffect.emit(event)
        }
    }

    private fun event2(event: Event) {
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
        object FetchPointList : Event()
        object BadRequestException : Event()
        object NullPointException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}
