import groovy.transform.Field

@Field String baseUrl = 'http://localhost:8285/market'
@Field String userEmail = 'admin@admin.com'
@Field String userPassword = 'admin123'
@Field String userName = 'Admin'

String createUser() {
    def createSession = new URL("${baseUrl}/user/create").openConnection()
    createSession.setRequestMethod("POST")
    def body = """{ "email": "${userEmail}","password": "${userPassword}", "name":"${userName}"} """
    createSession.setRequestProperty("Content-Type", "application/json")
    createSession.setDoOutput(true)
    createSession.getOutputStream().write(body.getBytes("UTF-8"))
    def code = createSession.getResponseCode()
    if (code) {
        def response = createSession.getInputStream().getText()
        return "Usuário criado com sucesso"
    }
    return null
}


String getUserToken() {
    def createSession = new URL("${baseUrl}/session").openConnection()
    createSession.setRequestMethod("POST")
    def body = """{ "userEmail": "${userEmail}","userPassword": "${userPassword}"} """
    createSession.setRequestProperty("Content-Type", "application/json")
    createSession.setDoOutput(true)
    createSession.getOutputStream().write(body.getBytes("UTF-8"))
    def code = createSession.getResponseCode()
    if (code) {
        def response = createSession.getInputStream().getText()
        def jsonResponse = new JsonSlurper().parseText(response)
        return jsonResponse.get("body").get("authToken")
    }
    return null
}


println createUser()

println getUserToken()