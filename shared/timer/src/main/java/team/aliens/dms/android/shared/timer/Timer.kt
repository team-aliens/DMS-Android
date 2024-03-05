package team.aliens.dms.android.shared.timer
/* TODO
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

*//**
 * Timer that can countdown or count up the time.
 * [StackOverFlow original idea](https://stackoverflow.com/questions/54095875/how-to-create-a-simple-countdown-timer-in-kotlin)
 *//*
class Timer constructor(
    val timeMillis: Long = 0L,
    private val job: CompletableJob = SupervisorJob(),
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + job),
) {
    private val timer: Job = makeCoroutinesTimer (
        delayMillis = 0,
        repeatMillis =
    ){

    }

    *//**
     * Makes coroutines timer.
     * @param delayMillis Gives delay before the count starts.
     * @param repeatMillis Count time.
     * @param action A lambda which is the main action of the timer, for example, logging can be invoked inside this lambda.
     *//*
    private fun makeCoroutinesTimer(
        delayMillis: Long = 0,
        repeatMillis: Long = 0,
        action: () -> Unit,
    ) = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    *//**
     * Resumes the timer if total time is not decreased, or resets and resumes.
     * @see [resume], [reset]
     *//*
    fun start() {

    }

    *//**
     * Pauses and reset the timer.
     *//*
    fun stop() {

    }

    *//**
     * Resets the timer.
     *//*
    fun reset() {

    }

    *//**
     * Resumes the timer.
     *//*
    fun resume() {

    }

    *//**
     * Pauses the timer.
     *//*
    fun pause() {

    }

    fun minus() {

    }

    fun plus() {

    }
}*/
