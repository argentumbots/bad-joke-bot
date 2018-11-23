import ch.menthe.io.PropertiesHandler
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import javax.security.auth.login.LoginException

object BadBot {
    private lateinit var jda: JDA

    @Throws(LoginException::class, InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val fileHandler = PropertiesHandler("config.properties")
        jda = JDABuilder(AccountType.BOT).setToken(fileHandler.getProperty("token")).buildBlocking()
        jda.addEventListener(CommandHandler())
    }
}