# Education_Java3

# Games Data Processing Project

## Cíl

Tento projekt se zaměřuje na práci se soubory (čtení/zápis) a kolekcemi v Javě. Využívá vstupní soubor `games.csv` k demonstraci různých operací s daty.

## Business zadání

Projekt se skládá z několika kroků:

1. **Datový model:**
    - Data ze souboru `games.csv` jsou načítána do datového modelu, který reflektuje strukturu těchto dat. Tento model využívá standardní Java kolekce (`List`, `Set`, `Map`).

2. **Generování souborů:**
    - **`game_genres.txt`:** Vygenerovaný soubor obsahující čárkou oddělený seznam všech žánrů her ze vstupního souboru. Seznam je abecedně seřazený a obsahuje pouze unikátní hodnoty.
    - **`simulator_games.csv`:** Vygenerovaný soubor obsahující dva sloupce: název a rok. Tento soubor obsahuje hry se žánrem "simulátor", které jsou seřazené podle data vydání.
    - **`game_publishers.csv`:** Vygenerovaný soubor obsahující dva sloupce: vydavatel a počet vydaných her. Tento soubor obsahuje vydavatele her a počet her, které vydali, seřazené od nejvíce produktivních vydavatelů.

## Testování

Projekt zahrnuje unit testování a manuální testování pro ověření správnosti všech funkcionalit. Testují se i nestandardní stavy, jako například neexistující nebo prázdný soubor.

## Souhrn

Tento projekt demonstruje praktické využití práce se soubory a kolekcemi v Javě, s důrazem na čtení a zápis dat, jejich třídění a filtrování. Výstupem jsou tři různé soubory s informacemi získanými ze vstupního souboru `games.csv`.
