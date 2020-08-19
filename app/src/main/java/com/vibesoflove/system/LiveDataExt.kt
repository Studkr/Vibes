package pro.shineapp.rentout.system.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

fun <T : Any, L : LiveData<T>> AppCompatActivity.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer(body))
}

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

fun <T : Any, L : Flow<T?>> Fragment.observe(stateFlow: L, body: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        stateFlow.filterNotNull().collect {
            body(it)
        }
    }
}


fun <T> LiveData<T>.toSingleLiveEvent(): LiveEvent<T> {
    val liveData = LiveEvent<T>()
    liveData.addSource(this) { value ->
        liveData.value = value
    }
    return liveData
}

fun Flow<Throwable?>.toErrorLiveEvent(): LiveEvent<Throwable> {
    return distinctUntilChanged { old, new ->
        if (old != null && new != null) {
            old::class == new::class
        } else {
            old == new
        }
    }.filterNotNull().asLiveData().toSingleLiveEvent()
}