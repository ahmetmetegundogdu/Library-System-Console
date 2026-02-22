package libraryapp

data class Book(
    val name: String ="",
    val author: String ="",
    val releaseDate: Int? =0,
    var isAvailable: Boolean =false,
    val bookId: Int? =0) {
    override fun toString(): String{
        return bookId.toString()+"-"+name+"-"+author+"-"+releaseDate+"-"+isAvailable
    }
}