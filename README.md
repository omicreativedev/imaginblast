# ImaginBlast
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-25-blue.svg)](https://www.oracle.com/java/technologies/javase/25-0-2-relnotes.html)
[![JavaFX](https://img.shields.io/badge/JavaFX-25.0.2-red.svg)](https://jdk.java.net/javafx25/)

> *"ImagInBlast: A wacky dream where you're a frog with a mission."*

## About

**ImaginBlast** is a top-down, 2D pixel rogue-lite shooter hybrid developed as the CS-335 Capstone Project at Simmons University (Spring 2026).

You play as a child trapped in a wacky dream as a frog. Blast your way through enemies, collect items, and defeat increasingly chaotic bosses across multiple dream levels.

## Gameplay

- **Genre:** Top-down shooter / Rogue-lite hybrid
- **Style:** 16-bit/8-bit retro pixel aesthetic
- **Controls:** WASD to move · Mouse to aim · Click to shoot
- **Collect:** Acorns, donuts, cupcakes, cassettes, bubbles
- **Enemies:** Squirrels, pillbugs, garlic, urchins
- **Bosses:** Cat Pirate, Beetle Chef, Broccoli King, Angry Grandma, Final Bunny

## Tech Stack

| Technology | Version |
|------------|---------|
| Java | 25.0.2+ |
| JavaFX | 25.0.2+ |
| IDE | Eclipse |

## Getting Started

### Prerequisites

- [Java JDK 25.0.2+](https://www.oracle.com/java/technologies/javase/25-0-2-relnotes.html)
- [JavaFX SDK 25.0.2+](https://jdk.java.net/javafx25/)
- Eclipse IDE (or any Java IDE)

### VM Arguments

In your Eclipse Run Configuration, add the following VM arguments — updating the `--module-path` to match your local JavaFX SDK install location:

```bash
--module-path "D:/Program Files/JavaFX/javafx-sdk-25.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.media --enable-native-access=javafx.graphics,javafx.media --add-exports javafx.base/com.sun.javafx=ALL-UNNAMED
```

### Running the Game

1. Clone the repository: `git clone https://github.com/omicreativedev/imaginblast`
2. Open the project in Eclipse
3. Set the VM arguments in your Run Configuration (see above)
4. Run `ImaginBlastMain.java`

## Game Progression

| Level | Area | Enemies | Boss |
|-------|------|---------|------|
| 1 | Forest Falls | Squirrels | Cheshire Cat Pirate |
| 2 | Muddy Ponds | Squirrels, Pillbugs | Beetle Chef |
| 3 | Broccoli Fields | Squirrels, Pillbugs, Garlic | Broccoli King (Broc) |
| 4 | Old Timey Fields | Squirrels, Pillbugs, Garlic, Urchins | Angry Grandma |
| Final | Dream's End | — | Final Bunny |

## Project Structure

```
ImaginBlast/
├── src/application/          # All game source files
│   ├── ImaginBlastMain.java  # Entry point
│   ├── Player.java           # Frog protagonist
│   ├── Boss*.java            # Boss classes
│   ├── Enemy*.java           # Enemy classes
│   ├── Item*.java            # Collectible items
│   ├── Level*.java           # Level definitions
│   └── ...
├── resources/                # Images, sounds, and assets
└── README.md
```

## Authors

| Author | GitHub |
|--------|--------|
| Ev | [@wholekatandkaboodle](https://github.com/wholekatandkaboodle) |
| Nyx | [@Nyx-Bigler](https://github.com/Nyx-Bigler) |
| Omi | [@omicreativedev](https://github.com/omicreativedev) |

## Future Plans

- Refinement of all existing levels
- Add 4–6 additional levels
- Maintain as an open source framework
- Help others build their own JavaFX projects
- Upload to itch.io
- Squash any leftover bugs

## License

This project is open source and available under the [MIT License](LICENSE).

---

*CS-335 Capstone Project · Simmons University · Spring 2026*
