package treelib.singleObjects.exceptions

open class NonExistentValueException : Exception {
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