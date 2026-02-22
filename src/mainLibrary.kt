package libraryapp

import java.io.File
import kotlin.random.Random
import kotlin.system.exitProcess
var bookList = mutableListOf<Book>()
var userList = mutableListOf<User>()
fun main(){
    readBooksDoc()
    readUsersDoc()
    while(true) {
        print("1-Kütüphaneye kitap ekle.\n" +
                "2-Kütüphaneden eski kitabı kaldır.\n" +
                "3-Kütüphaneye yeni kullanıcı ekle.\n" +
                "4-Kütüphanede bulunun kitapları listele\n" +
                "5-Kullanıcı işlemleri.\n" +
                "6-Kullanıcıları listele.\n"+
                "0-Çıkış yap\n")
        val selectedOption=readInt("Yapmak istediğiniz işlemi seçiniz:")
        when(selectedOption){
            0-> exitProcess(0)
            1-> {
                val bookName=readString("Lütfen kitabın ismini giriniz:")
                val authorName=readString("Lütfen kitabın yazarını giriniz:")
                val releaseDate=readInt("Lütfen kitabın çıkış yılını giriniz:")
                val bookId= Random.nextInt(0,1000)
                val newBook:Book = Book(bookName,authorName,releaseDate,bookId=bookId)
                bookList.add(newBook)
                saveBooksDoc()

            }
            2->{
                val bookName=readString("Kaldırılacak kitap ismini gir:")
                val findBook=bookList.find { it.name == bookName}
                bookList.remove(findBook)
            }
            3-> {
                val userName=readString("Yeni kaydedilecek üyenin ismi:")
                val userAge=readInt("Yeni kaydedilecek üyenin yaşı")
                val newUser:User=User(userName,userAge)
                userList.add(newUser)

            }
            4->{
                if(!bookList.isEmpty()){
                    for(i in bookList){
                        println(i.toString())
                    }
                }else{
                    println("Kitap listeniz boş.")
                }

            }
            5-> {
                userMenu()
            }
            6->{
                if(!userList.isEmpty()){
                    for (user in userList) {
                        println(user.name)
                    }
                }else{
                    println("Kullanıcı listeniz boş.")
                }

            }


        }

    }

}
fun userOperations(user: User?){

    while(true) {
        print("1-Kitap ödünç verme\n" +
                "2-Kitap geri iade işlemi"+
                "3-Üye kaydı silme\n" +
                "4-Kullanıcının sahip olduğu kitapları listele" )
        val selectedOption=readInt("Yapmak istediğiniz işlemi seçiniz:")
        when (selectedOption) {
            1->{
                val bookName=readString("Ödünç verilecek kitap adı:")
                val findBook=bookList.find { it.name == bookName}
                if(findBook!=null&&findBook.isAvailable!=false){
                    findBook.isAvailable=false
                    //user?.books?.add(findBook)

                }
            }
            2->{
                val bookName=readString("İade edilecek kitap adı:")
                var findBook=bookList.find { it.name == bookName}
                findBook?.isAvailable=true
                //var findBookUserList=user?.books?.find { it.name == bookName}
                //user?.books?.remove(findBookUserList!!)
            }
            3->{
                val chocie=readString("Kayıt silme işlemini onaylıyor musunuz?(Evet[E,e]-Hayır[H,h])")
                if(chocie.uppercase()=="E"){
                    userList.remove(user)
                }else{
                    userMenu()
                }
            }
            4->{
                for(i in user?.books!!){
                    println(i)
                }
            }

        }

    }


}
fun userMenu(){
    while(true) {
        println("1-Kayıtlı kullanıcı işlemleri\n" +
                "2-Yeni üye kaydı oluşturma\n" +
                "0-Ana menüye dön")
        var selectedOption=readInt("Yapmak istediğiniz işlemi seçiniz:")
        when (selectedOption) {
            1->{
                val userName=readString("Kullanıcı adını gir:")
                val findUser=userList.find { it.name == userName}
                userOperations(findUser)
            }
            2->{
                val userName=readString("Kullanıcının ismini gir:")
                val userAge=readInt("Kullanıcının yaşını gir")
                val newUser=User(userName,userAge)
                userList.add(newUser)
                saveUsersDoc()
            }
            0->{
                return
            }
        }
    }
}
fun readInt(prompt:String):Int{
    print(prompt)
    while(true){
        var input=readLine()?.toIntOrNull()
        if(input!=null){return input}
        println("Lütfen geçerli bir değer giriniz.")
    }
}
fun readString(prompt:String):String{
    print(prompt)
    while(true){
        var input=readLine().toString()
        if(!input.isNullOrEmpty()){return input}
        println("Lütfen geçerli bir değer giriniz.")
    }
}
fun readSource(fileName: String, message: String): List<String> {
    return try {
        val lines = File(fileName).readLines()
        println(message)
        lines
    } catch (e: Exception) {
        println("Hata oluştu: ${e.message}")
        emptyList()
    }
}
fun readBooksDoc(){
    for(line in readSource("books.txt","Kitap listesi okundu.")){
        var parts = line.split("-")
        var bookName=parts[0]
        var authorName=parts[1]
        var releaseDate=parts[2].toInt()
        var isAvailable=parts[3].toBoolean()
        var bookId=parts[4].toInt()
        var newBook=Book(bookName,authorName,releaseDate,isAvailable,bookId)
        bookList.add(newBook)
    }
}
fun readUsersDoc(){
    for(line in readSource("Users.txt","Kullanıcı listesi")){
        var parts = line.split("-")
        var userName=parts[0]
        var age=parts[1].toInt()
        var books=parts[2].split(",")
        var booksList = mutableListOf<String>()
        for(line in books){
            booksList.add(line)
        }
        var newUser=User(userName,age,booksList)
        userList.add(newUser)

    }
}
fun saveBooksDoc(){
    File("books.txt").writeText(bookList.joinToString("\n"){
        "${it.name}-${it.author}-${it.releaseDate}-${it.isAvailable}-${it.bookId}"
    })
}
fun saveUsersDoc(){
    File("users.txt").writeText(userList.joinToString("\n"){
        "${it.name}-${it.age}-${it.books.joinToString(separator=",",prefix="[",postfix="]")}"
    })
}
