public interface Hasher {

        String hashPassword(String password);

        boolean validatePassword(String password, String storedPassword);


}
