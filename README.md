# CS-19300-Final-Project---Meal-Tracker
Java program to track macros of meals

## The Problem

I want to solve calorie and macro tracking custom recipes for foods or drinks. Many calorie trackers don't have it for free or make it clunky to use.

## How It Works

Welcomes user and then asks user whether they want to view, add, update, or delete meals, as well as whether they want to exit the app. Meals are stored in a .txt file, Meals.txt, which will be stored locally and then read on start of the program

If they want to view:
 - reads a Meals.txt file 
     - if no file found, means no meals saved
 - outputs all the meals in the file and their calories

If they want to add:
 - Manual input:
     - Have user input Ingredient name, calories, fats, proteins, sugars, and carbs
     - Add Ingredient to Meal
 - Search (Only works if user sets up environment variable with their own USDA Food API key, instructions in set up):
     - User gives name of ingredient they want to add
     - Program searches USDA Food API
     - Adds ingredient to meal
  Both continuously ask to add ingredients until user says done and end with adding the meal with ingredients to the list of meals that will eventually be saved to Meals.txt

If they want to delete:
 - User inputs name of meal they want to delete
 - program deletes

If they want to update:
 - User inputs name of meal they want to update
 - Program finds meal and asks if they want to change, add, or remove ingredients from the meal.
 - Also asks if user wants to change name and/or servings of the meal

Original Design:
<img width="942" height="492" alt="CS 19300 Final Project Flowchart drawio (3)" src="https://github.com/user-attachments/assets/4856a55d-02ae-40d0-b0ab-b4787be26a03" />

Current Design:
<img width="1207" height="752" alt="UpdatedFinalProjectFlowchart drawio" src="https://github.com/user-attachments/assets/085258b5-2945-498f-9bd4-71f651f24466" />



## Tech Stack

- **Language:** Java
- **Libraries:** java.io, java.util, java.net.http, Gson
- **External tools:** USDA FoodData Central API

## Prerequisites

Before running this project, install:

- Java Development Kit (JDK) 25
- Visual Studio Code
- The Extension Pack for Java in VS Code

### Install VS Code and Java support

1. Download and install [Visual Studio Code](https://code.visualstudio.com/download).
2. Open VS Code.
3. Click the **Extensions** icon on the left sidebar.
4. Search for **Extension Pack for Java**.
5. Select the extension published by Microsoft and click **Install**.
6. Restart VS Code after installation.


## Set Up

Clone Repository

```bash
git clone https://github.com/Kchung567/CS-19300-Final-Project---Meal-Tracker.git
```

## Optional: USDA API Key Setup (Only do if you want to use the Search feature)

Create a free API key at https://fdc.nal.usda.gov/api-key-signup#top.
  - The sign up will request name and email
  - Key will come to the email you sign up with

Save the key as an environment variable named `FDC_API_KEY`. Different Methods explained below:
### Windows

1. Search Windows for **Edit environment variables for your account**.
2. Under **User variables**, click **New**.
3. Enter:
   - Variable name: `FDC_API_KEY`
   - Variable value: your USDA API key
4. Click **OK** and restart VS Code.

### macOS

1. Open **Terminal**.
2. Run:

   ```bash
   nano ~/.zshrc
   ```
3. Add line, but use the API key received in place of "YOUR_API_KEY"
   ```bash
   export FDC_API_KEY="YOUR_API_KEY"
   ```
4. Save with Ctrl + O, press Enter, then exit with Ctrl + X.
5. Run
   ```bash
   source ~/.zshrc
   ```
6. Restart VS Code. 

### Linux
1. Open **Terminal**.
2. Run:

   ```bash
   nano ~/.bashrc
   ```
3. Add line, but use the API key received in place of "YOUR_API_KEY"
   ```bash
   export FDC_API_KEY="YOUR_API_KEY"
   ```
4. Save with Ctrl + O, press Enter, then exit with Ctrl + X.
5. Run
   ```bash
   source ~/.bashrc
   ```
6. Restart VS Code


Do not add your API key to the source code, README, or GitHub.

## Usage

Open repository folder in VS Code
 - VS Code
 - File -> Open Folder -> Open Cloned Repo
 - Make sure CS-19300-Final-Project---Meal-Tracker is in the top panel
 - Wait for Java Projects to load
 - Open Main.java
 - Click the run button that appears directly above public static void main(String[] args)
 - If run button doesn't appear, wait a minute longer; most likely java projects have not fully loaded in yet


## Roadmap

- [x] Include Macros such as protein, fats, carbs, and sugars
- [ ] Import files of saved meals
- [x] Add welcome and goodbye messages

## Author

Kaden Chung, https://github.com/Kchung567
