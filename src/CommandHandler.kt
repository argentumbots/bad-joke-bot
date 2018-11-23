import ch.menthe.io.PropertiesHandler
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.util.*

class CommandHandler : ListenerAdapter() {

    private val propertiesHandler = PropertiesHandler("badguys.properties")

    internal var badReplies = arrayOf(
        "Write a koma and never wake up again!",
        "Wana know how I escaped Iraq?\nIran!",
        "Syriasly?!",
        "Ich han viili switches dihei",
        "YKing bechunt direkt 100 Pünkt XD!!!",
        "What did the buffalo say when his son left?\nBison!"
    )

    override fun onMessageReceived(event: MessageReceivedEvent?) {
        val content = event!!.message.contentRaw
        val channel = event.channel
        //        if (!event.getAuthor().equals(BadBot.jda.getSelfUser())){
        //            channel.sendMessage("bongobongo").queue();
        //        }

        if (event.message.contentRaw.startsWith(">bad")) {
            val member = event.message.mentionedMembers[0]
            channel.sendMessage(
                "```" + member.effectiveName + "!! Nöd guet, nicht gut!\n"
                        + "+1 Punkt\n"
                        + "Counter Joke:\n"
                        + badReplies[Random().nextInt(badReplies.size)] + "```"
            ).queue()

            var found = false

            if (propertiesHandler.keys.contains(member.user.name)) {
                val points = Integer.parseInt(propertiesHandler.getProperty(member.user.name)) + 1
                propertiesHandler.setValue(member.user.name, (points).toString())
                found = true
            }
            if (!found) {
                propertiesHandler.setValue(member.user.name, "1")
            }
        } else if (content.equals(">worst", ignoreCase = true)) {
            var result = "Schlechti Joker:```"

            val guys = ArrayList<BadGuy>()

            for (user in propertiesHandler.getKeys()) {
                val badGuy = BadGuy
                badGuy.name = user
                badGuy.points = Integer.valueOf(propertiesHandler.getProperty(user))
                guys.add(badGuy)
            }

            for (badGuy in guys) {
                result = result + badGuy.points + "pt. " + badGuy.name + "\n"
            }
            channel.sendMessage("$result```").queue()
        } else if (event.message.contentRaw.toLowerCase().startsWith(">jewlevel")) {
            try {
                val member = event.message.mentionedMembers[0]
                val jewLevel = hasNextJewLevel(0)
                var message = ("```" + member.user.name + " hät en Judelevel vo "
                        + jewLevel + "```")

                if (member.user === event.author) {
                    if (jewLevel == 0) {
                        var points = Integer.parseInt(propertiesHandler.getProperty(member.user.name))
                        points -= 1
                        propertiesHandler.setValue(member.user.name, (points).toString())
                        message = "$message```Guter Mann 1 Punkt wurde von deinem  Schlechten witz profil entfernt:)```"
                    } else {
                        var points = Integer.parseInt(propertiesHandler.getProperty(member.user.name))
                        points += jewLevel
                        propertiesHandler.setValue(member.user.name, (points).toString())
                        message = "$message```Jew exposed +$jewLevel uf dim record... >:(```"
                    }
                }
                channel.sendMessage(message).queue()
            } catch (e: IndexOutOfBoundsException) {
                channel.sendMessage(
                    "```Mention en User zum teste\n" +
                            ">jewlevel @User```"
                ).queue()
            }
        }
    }

    private fun hasNextJewLevel(jewlevel: Int): Int {
        var jewlevel = jewlevel
        if (Random().nextBoolean()) {
            jewlevel++
            return hasNextJewLevel(jewlevel)
        } else {
            return jewlevel
        }
    }


}
