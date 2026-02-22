package libraryapp

data class User(var name: String ="", var age: Int? =0,val books: MutableList<String> = mutableListOf()) {
    fun addBook(newBook: String?) {
        if(newBook!=null){
            books.add(newBook)
        }
    }
    fun MutableList<String>.printBookslist(): String{
        var idList:String = ""
        for(it in this){
            idList +="${it.toString()}-"
        }
        return idList
    }
    override fun toString(): String {
        return "Üyenin ismi:${name} \n" +
                "Üyenin yaşı:${age} \n" +
                "Üyenin ödünç aldığı kitaplar:\n${books.printBookslist()} \n"
    }

}