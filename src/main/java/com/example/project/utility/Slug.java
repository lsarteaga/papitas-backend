package com.example.project.utility;

import com.github.slugify.Slugify;

public class Slug {
    public static String makeSlug(String input) {
        if (input == null)
            throw new IllegalArgumentException();
        Slugify slugify = new Slugify();
        return slugify.slugify(input);
    }
}
