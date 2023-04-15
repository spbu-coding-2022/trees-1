package treelib.singleObjects.exceptions

class IllegalNodeStateException : Exception {
    constructor() : super(
        "There is no node required by the condition of the algorithm"
    )

    constructor(message: String) : super(
        "There is no node required by the condition of the algorithm: $message",
    )

    constructor(message: String, cause: Throwable) : super(
        "There is no node required by the condition of the algorithm: $message",
        cause,
    )

    constructor(cause: Throwable) : super(cause)
}
