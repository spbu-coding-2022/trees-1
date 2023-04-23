package treelib.commonObjects.exceptions

open class VauleNotFound : Exception {
    constructor() : super(
        "Value doesn't exist in the tree"
    )

    constructor(message: String) : super(
        "Value doesn't exist in the tree $message",
    )

    constructor(message: String, cause: Throwable) : super(
        "Value doesn't exist in the tree -> $message",
        cause,
    )

    constructor(cause: Throwable) : super(cause)
}
