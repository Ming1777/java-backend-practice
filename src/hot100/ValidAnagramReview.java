package hot100;

public class ValidAnagramReview {
    public static void main(String[] args) {

        String s = "anagram";
        String t = "nagaram";

        boolean result = isAnagram(s,t);
        System.out.println(result);

    }
    public static boolean isAnagram(String s,String t){
        if (s.length() != t.length() || s.equals(t)){
            return false;
        }
        int[] counts = new int[26];

        for (char c : s.toCharArray()){
            counts[c - 'a']++;
        }

        for (char c : t.toCharArray()) {
            counts[c - 'a']--;
        }

        for (int count : counts){
            if (count != 0){
                return false;
            }
        }
        return true;
    }
}
