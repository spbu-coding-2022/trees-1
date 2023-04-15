package treelib.singleObjects.exceptions

open class ImpossibleCaseException : Exception {
    constructor() : super(
        "This case is impossible, otherwise there is an error in the program code",
    )

    constructor(message: String) : super(
        "This case is impossible, otherwise there is an error in the program code -> $message",
    )

    constructor(message: String, cause: Throwable) : super(
        "This case is impossible, otherwise there is an error in the program code -> $message",
        cause,
    )

    constructor(cause: Throwable) : super(cause)
}
