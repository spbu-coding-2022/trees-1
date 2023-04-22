package treelib.commonObjects.exceptions

class IllegalBaseNodeException : Exception {
    constructor() : super(
        "A non-existent node (null) was passed to the method"
    )

    constructor(message: String) : super(
        "A non-existent node (null) was passed to the method: $message",
    )

    constructor(message: String, cause: Throwable) : super(
        "A non-existent node (null) was passed to the method: $message",
        cause,
    )

    constructor(cause: Throwable) : super(cause)
}
