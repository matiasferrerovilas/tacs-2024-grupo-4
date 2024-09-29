package tacs.grupo_4.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tacs.grupo_4.telegramBot.handlers.UsuarioHandler;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final UsuarioHandler usuarioHandler;
    @Autowired
    public TelegramBot(@Value("${telegram.bot.token}") String botToken,
                       @Lazy UsuarioHandler usuarioHandler) {
        super(botToken);  // non-deprecated constructor
        this.usuarioHandler = usuarioHandler;
    }

    @Override
    public String getBotUsername() {
        return "@hyperdestroyerbot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            Long telegramUserId = update.getMessage().getFrom().getId();
            String nombre = update.getMessage().getFrom().getFirstName();

            int primerEspacioIndex = messageText.indexOf(' ');
            String comando;
            String[] parametros;
            if (primerEspacioIndex == -1) {
                comando = messageText.toLowerCase();
                parametros = new String[0];
            } else {
                comando = messageText.substring(0, primerEspacioIndex).toLowerCase();
                String parametrosString = messageText.substring(primerEspacioIndex + 1);
                parametros = parametrosString.split(",");
            }
            String respuesta = switch (comando) {
                case "hola"             ->      "chau";
                case "help"             ->      helpMensaje;
                case "whoami"           ->      usuarioHandler.whoami(parametros, chatId, telegramUserId);
                case "crearusuario"     ->      usuarioHandler.crearUsuario(parametros, chatId, telegramUserId);
                default                 ->      bienvenida(nombre);
            };
            if (!respuesta.isEmpty()) {
                enviarMensaje(chatId, respuesta);
            }
        }
    }
    private final String helpMensaje =
            """
                    Comandos disponibles:
                    • crearUsuario <nombre>,<email>
                    • hola
                    • whoami
            """;
    private String bienvenida(String nombre) {
        return "¡Hola " + nombre + "! Soy el TicketBot."
                + " Puedes utilizar 'help' para ver las operaciones disponibles";
    }

    public void enviarMensaje(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
