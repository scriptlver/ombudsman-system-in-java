import java.util.Date;

enum ManifestationType {
    SUGGESTION,
    PRAISE,
    CRITICIZE
}

interface Auditable {
    String retrieveAuthor();
    Date retrieveDate();
}

class Manifestation implements Auditable {
    private String description;
    private Date manifestationDate;
    private User user;
    private ManifestationType type;

    public Manifestation(String description, Date manifestationDate, User user, ManifestationType type) {
        this.description = description;
        this.manifestationDate = manifestationDate;
        this.user = user;
        this.type = type;
    }

    @Override
    public String retrieveAuthor() {
        return user.getName();
    }

    @Override
    public Date retrieveDate() {
        return manifestationDate;
    }

    public String getDescription() {
        return description;
    }

    public ManifestationType getType() {
        return type;
    }
}
