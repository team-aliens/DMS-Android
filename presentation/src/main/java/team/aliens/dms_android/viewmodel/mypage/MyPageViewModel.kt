package team.aliens.dms_android.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.mypage.Gender
import team.aliens.dms_android.feature.mypage.MyPageEvent
import team.aliens.dms_android.feature.mypage.MyPageState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType
import team.aliens.domain.exception.*
import team.aliens.domain.usecase.mypage.RemoteMyPageUseCase
import team.aliens.domain.usecase.mypage.RemotePointUseCase
import team.aliens.domain.usecase.students.RemoteStudentWithdrawUseCase
import team.aliens.domain.usecase.user.SignOutUseCase
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val remoteMyPageUseCase: RemoteMyPageUseCase,
    private val remotePointListUseCase: RemotePointUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val withdrawUseCase: RemoteStudentWithdrawUseCase,
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
                signOutUseCase.execute(Unit)
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
                withdrawUseCase.execute(Unit)
            }.onSuccess {

            }
        }
    }

    internal fun fetchMyPage() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteMyPageUseCase.execute(Unit)
            }.onSuccess {
                state.value.myPageEntity.run {
                    gcn = it.gcn
                    bonusPoint = it.bonusPoint
                    minusPoint = it.minusPoint
                    name = it.name
                    phrase = it.phrase
                    schoolName = it.schoolName
                    profileImageUrl = it.profileImageUrl
                    sex = Gender.valueOf(it.sex)
                }
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

    fun fetchPointList(pointType: PointType) {
        viewModelScope.launch {
            kotlin.runCatching {
                remotePointListUseCase.execute(pointType)
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
        data class FetchPointList(val pointListEntity: PointListEntity) : Event()
        object BadRequestException : Event()
        object NullPointException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}
