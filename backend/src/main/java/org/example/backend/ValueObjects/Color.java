package org.example.backend.ValueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Color {
    private String colorName;
    private String colorHex;
}
