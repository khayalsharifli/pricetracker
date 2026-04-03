package az.khayalsharifli.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

/**
 * focusOn: For passing state down the tree.
 * Returns a new reader. Does NOT trigger recomposition in the caller.
 */
@Composable
fun <T, R> (() -> T).focusOn(selector: T.() -> R): () -> R {
    val parentProvider = this
    val derivedSlice = remember(parentProvider) {
        derivedStateOf { selector(parentProvider()) }
    }
    return { derivedSlice.value }
}

/**
 * read: For reading state in the current Composable.
 * Returns the raw value. Triggers recomposition ONLY if the selected value changes.
 */
@Composable
fun <T, R> (() -> T).read(selector: T.() -> R): R {
    val parentProvider = this
    val derivedSlice = remember(parentProvider) {
        derivedStateOf { selector(parentProvider()) }
    }
    return derivedSlice.value
}
