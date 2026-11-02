# QuizGame - TP Android Quiz en Images

Un projet d'application Android développé en Kotlin et XML dans le cadre du Travail Pratique "Quiz en Images".

## 📱 Présentation
L'application est un jeu de quiz éducatif composé de 5 questions illustrées sur le thème des **formes géométriques**. Pour chaque question :
- L'utilisateur observe une image vectorielle (Triangle, Carré, Cercle, Étoile, Rectangle).
- Il choisit une réponse parmi 3 boutons.
- Un retour visuel immédiat indique si la réponse est correcte ou incorrecte.
- La progression (0 à 5) et le score (0 à 5) sont mis à jour en temps réel.
- À la fin des 5 questions, le score final est présenté avec la possibilité de rejouer.

---

## 🚀 Caractéristiques Techniques
- **Langage :** Kotlin
- **Interface :** Fichiers XML déclaratifs avec View Binding (`ActivityMainBinding`)
- **Architecture :** Activité unique (`MainActivity`), sans fragment
- **Thème & UI :** Material Design Components, `ConstraintLayout`, `ScrollView` pour petits écrans
- **Gestion du Lifecycle :** Conservation de l'état du jeu lors des rotations d'écran (`onSaveInstanceState`)
- **Accessibilité :** Descriptions textuelles pour toutes les images (`contentDescription`)

---

## 🛠️ Structure du Projet
```text
QuizGame/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/quizgame/
│           │   ├── MainActivity.kt        # Logique principale du quiz et gestion des événements
│           │   └── Question.kt            # Data class représentant une question
│           └── res/
│               ├── drawable/              # Images vectorielles XML des formes géométriques
│               │   ├── ic_shape_triangle.xml
│               │   ├── ic_shape_square.xml
│               │   ├── ic_shape_circle.xml
│               │   ├── ic_shape_star.xml
│               │   └── ic_shape_rectangle.xml
│               ├── layout/
│               │   └── activity_main.xml  # Layout XML de l'application
│               └── values/
│                   ├── colors.xml         # Couleurs personnalisées
│                   └── strings.xml        # Chaînes de caractères traduites
├── compte-rendu/
│   └── compte_rendu.md                    # Compte rendu détaillé du TP
└── README.md
```

---

## ⚙️ Instruction d'Installation et d'Exécution
1. Cloner le dépôt GitHub :
   ```bash
   git clone <URL_DU_DEPOT_GITHUB>
   ```
2. Ouvrir le projet dans **Android Studio** (Koala / Ladybug / 2024.1+).
3. Laisser Gradle synchroniser les dépendances.
4. Lancer l'application sur un émulateur ou un appareil Android physique (Android 7.0 / API 24 minimum).

---

## 📋 Compte Rendu et Captures
- Le compte rendu détaillé répondant aux questions du TP se trouve dans le fichier [`compte-rendu/compte_rendu.md`](compte-rendu/compte_rendu.md).
- Les captures d'écran de l'application sont situées dans le dossier `captures/`.
