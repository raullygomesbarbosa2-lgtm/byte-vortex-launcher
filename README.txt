BYTE VORTEX LAUNCHER — projeto inicial Android

O que faz:
- Funciona como aplicativo de tela inicial (HOME) no Android.
- Mostra os aplicativos instalados e permite abri-los.
- Visual escuro com identidade neon azul/ciano.
- Não substitui nem remove o Android 15.

Como gerar o APK:
1. Abra este projeto no Android Studio em um computador.
2. Aguarde a sincronização do Gradle.
3. Use Build > Build Bundle(s) / APK(s) > Build APK(s).
4. O APK de depuração ficará em app/build/outputs/apk/debug/app-debug.apk.

Como ativar no Motorola:
1. Instale o APK.
2. Pressione o botão Início.
3. Escolha BYTE VORTEX e selecione Sempre.
   Ou vá em Configurações > Apps > Apps padrão > App de início
   (o nome do menu pode variar) e escolha BYTE VORTEX.

Observações:
- Este pacote contém o código-fonte, não um APK já compilado.
- A permissão QUERY_ALL_PACKAGES é usada para listar apps instalados.
- Não inclui emuladores nem jogos; apenas abre os apps que você instalar.
- Projeto inicial: teste no aparelho antes de usar como tela inicial padrão.
