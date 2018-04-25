package life.qbic.presenter.utils;

/**
 * @author fhanssen
 *
 */
public final class LabelFormatter {

    /**
     * For general label: Each word starts with a capital letter
     */
    public static String generateCamelCase(String label){

        if(label == null || label.isEmpty())
            return label;

        String[] words = label.split("\\W+");

        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(words[0].charAt(0)));

        if(words[0].length() > 1) {
            sb.append(words[0].substring(1).toLowerCase());
        }

        for(int i = 1; i <words.length; i++){
            sb.append(" "); sb.append(Character.toUpperCase(words[i].charAt(0)));

            if(words[i].length() > 1) {
                sb.append(words[i].substring(1).toLowerCase());
            }


        }

        return sb.toString();
    }

    /**
     *
     * @param label
     * @return
     */
    public static String firstUpperRestLowerCase(String label){

        if(label == null || label.isEmpty())
            return label;

        String[] words = label.split("\\W+");

        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(words[0].charAt(0)));
        if(words[0].length() > 1) {
            sb.append(words[0].substring(1).toLowerCase());
        }

        for(int i = 1; i < words.length; i++){
            sb.append(" ");
            sb.append(words[i].toLowerCase());
        }

        return sb.toString();

    }

}
