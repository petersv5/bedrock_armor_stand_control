# Contributing to Bedrock Armor Stand Control (BASC)

Contributions help improve the mod and ensure compatibility with other mods. Please read the guidelines below before submitting a pull request or issue.

## 📝 How to Contribute

### 🐛 Reporting Bugs
If you've found a bug, please open an issue on GitHub with the following details:
- **Description**: What happened? What did you expect to happen?
- **Steps to Reproduce**: Provide a step-by-step guide to reproduce the issue.
- **Logs & Crash Reports**: Include relevant logs or crash reports (use a pastebin-like service if needed).
- **Mod Version & Environment**: BASC, Minecraft, Fabric, Floodgate versions and any other relevant mods installed.

### 💡 Suggesting Features
Feature requests are welcome! When suggesting a new feature:
- Keep discussions constructive and open-ended for potential improvements.

## ⚙️ Development Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/petersv5/bedrock_armor_stand_control.git
   ```
2. Set up the development environment:
    - Ensure you have a suitable JDK for your Minecraft version installed.
    - Import the project into your IDE (optional)
    - Let gradle to its job importing the project and start coding :-)
3. Build the project:
   ```sh
   ./gradlew build
   ```

## 🖋️ Code Style & Conventions
- Follow standard **Java conventions** (use proper naming, avoid unnecessary complexity).
- Use spaces for indentation, **not tabs**.
- Keep methods **short and focused**—avoid large, complex methods.

## 🔄 Pull Request Guidelines
1. **Create a new branch** for your feature or bug fix:
   ```sh
   git checkout -b feat/your-feature
   ```
2. **Please target the [main branch](https://github.com/petersv5/bedrock_armor_stand_control/tree/main)**. Only the latest Minecraft / Geyser versions are supported.
4. **Keep PRs focused**—avoid bundling multiple changes in a single PR.
3. **Write clear commit messages** describing what each change does.
4. **Test your changes** before submitting.
5. If your PR references an issue, **link it in the description** (e.g., `Closes #42`).

## 📜 License
By contributing, you agree that your contributions will be licensed under the same license as BASC.

