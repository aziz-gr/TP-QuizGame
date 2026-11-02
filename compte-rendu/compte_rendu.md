# Compte Rendu : TP Android - Quiz en Images

## 1. Informations Générales
* **Thème choisi :** Formes géométriques (Triangle, Carré, Cercle, Étoile, Rectangle).
* **Technologies utilisées :** Kotlin, Android SDK, View Binding, ConstraintLayout, Material Components.

---

## 2. Présentation de l'Interface et des Widgets Utilisés
L'interface utilisateur a été conçue dans une activité unique (`MainActivity`) via le fichier de layout XML `activity_main.xml`.

* **`MaterialCardView` (En-tête fixe) :** Contient le titre de l'application et le score actuel affiché en haut à droite.
* **`ProgressBar` (Horizontale) :** Placée juste sous l'en-tête, elle indique le nombre de questions répondues (de 0 à 5).
* **`ScrollView` :** Enveloppe le contenu pour garantir que l'interface reste parfaitement lisible et utilisable sur les petits écrans.
* **`ImageView` (`img_question`) :** Affiche l'image vectorielle de la question courante (`scaleType="fitCenter"` pour éviter toute déformation) et intègre une description d'accessibilité (`contentDescription`).
* **`TextView` (`txt_question` & `txt_question_num`) :** Affichent l'intitulé de la question et son numéro (ex: *Question 1 sur 5*).
* **`MaterialButton` (`btn_choix1`, `btn_choix2`, `btn_choix3`) :** Trois boutons distincts proposant les choix de réponse.
* **`TextView` (`txt_feedback`) :** Affiche le retour visuel (succès en vert ou échec avec indication de la bonne réponse en rouge) après la validation.
* **`MaterialButton` (`btn_next`) :** Bouton d'action pour passer à la question suivante ou afficher le résultat final (désactivé tant qu'aucune réponse n'est choisie).
* **`LinearLayout` (`layout_result`) :** Zone de résultat final affichée à la fin du jeu, contenant le score total et le bouton **Rejouer** (`btn_restart`).

---

## 3. Structure des Données et Association des Images

Les questions sont représentées par une `data class` nommée `Question` :

```kotlin
data class Question(
    val questionText: String,
    @param:DrawableRes val imageResId: Int,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val contentDescription: String
)
```

Chaque instance associe :
* Un texte explicite.
* L'identifiant de ressource d'une image vectorielle XML dans `res/drawable` (`R.drawable.ic_shape_triangle`, etc.).
* Une liste de 3 choix (Strings).
* L'indice (0, 1 ou 2) correspondant au choix correct dans la liste.
* Un texte pour l'accessibilité.

Un tableau/liste statique `questions` de 5 éléments est déclaré au niveau de `MainActivity`.

---

## 4. Réponses aux Questions du Compte Rendu

### Q1. Comment représentez-vous une question et sa bonne réponse ?
Une question est représentée par une instance de la data class `Question`. La bonne réponse est spécifiée par l'attribut `correctAnswerIndex` qui pointe vers l'indice (0, 1 ou 2) du choix correct dans la liste `options`.

### Q2. Comment le clic sur un bouton permet-il de savoir quel choix a été sélectionné ?
Chaque bouton possède un écouteur de clic (`setOnClickListener`) qui transmet son indice respectif (0 pour `btnChoix1`, 1 pour `btnChoix2`, 2 pour `btnChoix3`) à la méthode `onChoiceSelected(choiceIndex: Int)`.

### Q3. Comment empêchez-vous l'attribution de plusieurs points pour une question ?
Dès qu'un bouton de choix est cliqué, un booléen `hasAnsweredCurrentQuestion` passe à `true` et les trois boutons sont immédiatement désactivés (`isEnabled = false`). Si la méthode `onChoiceSelected` est rappelée alors que `hasAnsweredCurrentQuestion` est déjà vrai, l'exécution s'interrompt immédiatement (`if (hasAnsweredCurrentQuestion) return`).

### Q4. Quelle différence faites-vous entre le score et la progression ?
* **Le score :** Mesure le nombre de *bonnes* réponses obtenues par l'utilisateur (augmente seulement en cas de bonne réponse, valeur de 0 à 5).
* **La progression :** Mesure le nombre de *questions traitées/répondues* (augmente de 1 à chaque réponse donnée, qu'elle soit juste ou fausse, valeur de 0 à 5).

### Q5. Comment détectez-vous la dernière question et affichez-vous le résultat ?
La dernière question est détectée lorsque `currentQuestionIndex == questions.size - 1`. À cet instant, le libellé du bouton `btn_next` devient *"Voir le résultat"*. Lors du clic sur ce bouton à la fin de la 5ème question, la fonction `showFinalResults()` masque le conteneur du quiz (`layout_quiz.visibility = View.GONE`) et affiche le conteneur du résultat (`layout_result.visibility = View.VISIBLE`) dans la même activité.

---

## 5. Gestion de la Rotation de l'Écran
Pour éviter la perte d'état lors du changement d'orientation de l'appareil, l'état du jeu est sauvegardé via `onSaveInstanceState` :
* `KEY_CURRENT_INDEX`
* `KEY_SCORE`
* `KEY_HAS_ANSWERED`
* `KEY_SELECTED_CHOICE`
* `KEY_GAME_OVER`

Dans `onCreate`, l'application restaure ces variables pour réafficher exactement la question courante avec son état (répondu ou non, feedback affiché, boutons activés/désactivés).

---

## 6. Tests Réalisés et Validation

| Scénario de test | Résultat Obtenu | Statut |
| :--- | :--- | :---: |
| **Nouvelle partie** | 1ère question affichée, score 0/5, progression 0/5, 3 choix actifs | **CONFORME** |
| **Bonne réponse** | Score +1, progression +1, feedback vert, choix désactivés | **CONFORME** |
| **Mauvaise réponse** | Score inchangé, progression +1, feedback rouge + correction | **CONFORME** |
| **Clics multiples sur choix** | Boutons désactivés dès le 1er clic, aucun point en plus | **CONFORME** |
| **Navigation inter-questions** | Question suivante chargée avec nouveaux choix et image | **CONFORME** |
| **Dernière question** | Bouton "Voir le résultat" affiché et fonctionnel | **CONFORME** |
| **Écran final** | Score final exact affiché (0 à 5/5) dans la même activité | **CONFORME** |
| **Bouton Rejouer** | Réinitialisation complète du quiz et du score à zéro | **CONFORME** |
| **Rotation de l'écran** | Conservation parfaite de la question, du score et de l'état | **CONFORME** |
