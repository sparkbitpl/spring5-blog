package pl.sparkbit.restdocs.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by Tomasz Kopczynski.
 */
@AllArgsConstructor
@Builder
@Getter
public class Document {

    private final String author;
    private final String title;

}
