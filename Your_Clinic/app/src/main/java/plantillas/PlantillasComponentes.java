package plantillas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputFilter;
import android.text.Spanned;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public class PlantillasComponentes {



    public static boolean fechaValida(String fecha, EditText campo) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Si no es válida, lanzará una excepción
            LocalDate.parse(fecha, formatter);

            return true;
        } catch (DateTimeParseException e) {
            campo.setError(null);
            return false;
        }
    }
    public static boolean horaValida(String hora, EditText campo) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(hora, formatter);
            return true;
        } catch (DateTimeParseException e) {
            campo.setError(null);
            return false;
        }
    }




    public static void aplicarFiltroSoloLetras(EditText editText) {

        // 1. Definir la expresión regular
        // [a-zA-Z] -> Letras básicas
        // \\s      -> Espacios en blanco
        // ÁÉÍÓÚáéíóúÑñ -> Caracteres específicos en español
        final Pattern patronSoloLetras = Pattern.compile("[a-zA-Z\\sÁÉÍÓÚáéíóúÑñ]*");

        // 2. Crear la instancia del InputFilter
        InputFilter filtroLetras = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                // Obtener el fragmento de texto que el usuario está intentando introducir
                String textoPropuesto = source.subSequence(start, end).toString();

                // Si el texto propuesto (incluyendo las letras que ya están) NO coincide
                // con el patrón, se rechaza.
                if (patronSoloLetras.matcher(textoPropuesto).matches()) {
                    return null; // Acepta la entrada (retorna null)
                }

                return ""; // Rechaza la entrada (retorna una cadena vacía)
            }
        };

        // 3. Aplicar el filtro al EditText
        // Creamos un array que contiene solo nuestro nuevo filtro.
        editText.setFilters(new InputFilter[] { filtroLetras });
    }

}
