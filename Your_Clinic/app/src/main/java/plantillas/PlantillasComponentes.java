package plantillas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
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

    public void configurarFormatoHora(final EditText editTextHora) {
        editTextHora.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final char separator = ':'; // El separador de horas y minutos

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se usa
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Guardamos el texto actual antes de que cambie
                current = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(current)) {
                    return; // Evita el bucle infinito si el texto no cambia realmente
                }

                // 1. Limpiamos el texto, dejando solo los dígitos
                String clean = s.toString().replaceAll("[^\\d]", "");
                int cl = clean.length();

                // Limitamos la entrada a un máximo de 4 dígitos (para HHMM)
                if (cl > 4) {
                    cl = 4;
                    clean = clean.substring(0, 4);
                }

                int sel = cl; // Posición inicial del cursor

                // 2. Lógica de inserción del separador (:)
                if (cl >= 2) {
                    // Insertamos el separador después de las horas (posición 2)
                    clean = clean.substring(0, 2) + separator + clean.substring(2);
                    sel++; // Movemos el cursor para saltar el separador
                }

                // 3. Reemplazamos el texto del EditText con el formato limpio
                if (!clean.equals(s.toString())) {
                    s.replace(0, s.length(), clean);
                }

                // 4. Ajustamos la posición del cursor
                sel = sel > s.length() ? s.length() : sel;
                editTextHora.setSelection(sel);
            }
        });
    }

    public void configurarFormatoFecha(final EditText editTextFecha) {
        editTextFecha.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String format = "DDMMYYYY"; // Usado para la lógica de conteo
            private final char separator = '-'; // Puedes cambiar a '/' si lo prefieres

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se usa
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Guardamos el texto actual antes de que cambie
                current = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(current)) {
                    return; // Evita el bucle infinito si el texto no cambia realmente
                }

                String clean = s.toString().replaceAll("[^\\d]", "");
                String cleanC = current.replaceAll("[^\\d]", "");

                int cl = clean.length();
                int sel = cl; // Posición del cursor

                for (int i = 2; i <= 6 && cl > i; i += 2) {
                    // Inserta el separador en las posiciones 2 y 4 (después de DD y MM)
                    sel++;
                }

                if (clean.equals(cleanC)) sel--; // Mueve el cursor hacia atrás si se eliminó el separador

                if (cl < 8){ // Máximo 8 dígitos para la fecha (DDMMYYYY)

                    // 1. Inserta el separador después del día (posición 2)
                    if (cl > 2 && cl < 5) {
                        clean = clean.substring(0, 2) + separator + clean.substring(2);
                    }
                    // 2. Inserta el separador después del mes (posición 5, que incluye el primer guion)
                    else if (cl >= 5) {
                        clean = clean.substring(0, 2) + separator + clean.substring(2, 4) + separator + clean.substring(4);
                    }
                } else if (cl == 8) {
                    // Formato final DD-MM-AAAA
                    clean = clean.substring(0, 2) + separator + clean.substring(2, 4) + separator + clean.substring(4, 8);
                }

                // Si el texto formateado es diferente, se actualiza el EditText
                if (!clean.equals(s.toString())) {
                    s.replace(0, s.length(), clean);
                }

                // Ajusta la posición del cursor
                sel = sel < 0 ? 0 : sel;
                sel = sel > s.length() ? s.length() : sel;
                editTextFecha.setSelection(sel);
            }
        });
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
