import org.koin.mp.KoinPlatformTools
import org.koin.test.KoinTest

inline fun <reified T : Any> KoinTest.injectProp(
    key: String,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
): Lazy<T> = lazy(mode) { getKoin().getProperty<T>(key) ?: throw Error("Key $key not found!") }
