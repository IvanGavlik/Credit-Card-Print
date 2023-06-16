package hr.rba.creditcardissuing.issuing;

import java.util.Objects;

public final class IssuingDto {
    private String userName;

    /**
     * @return name.
     */
    public String getName() {
        return userName;
    }

    /**
     * @param name set.
     */
    public void setName(final String name) {
        this.userName = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IssuingDto that = (IssuingDto) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

}
