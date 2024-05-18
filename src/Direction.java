public enum Direction {
    STAY("\"Стоит\""), UP("\"Вверх\""), DOWN("\"Вниз\"");
    private final String title;
    Direction(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
