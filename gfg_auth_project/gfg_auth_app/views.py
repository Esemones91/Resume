from django.shortcuts import render

# Create your views here.

# Home View
def home(request):
    """Display a simple login link

    Args:
        request (_type_): _description_
    """
    return HttpResponse("Welcome! <a href='/google/login/'>Login with Google</a>")

# Google Login View
def google_login(request):
    """Initialize the OAuth2 flow and redirect users to the Google consent page.

    Args:
        request (_type_): _description_
    """
    flow = InstalledAppFlow.from_client_secrets_file(CLIENT_SECRETS_FILE, SCOPES)
    flow.redirect_uri = REDIRECT_URI

    authorization_url, state = flow.authorization_url(access_type='offline', prompt='consent')
    request.session['state'] = state  # Store state in session
    return redirect(authorization_url)

# Google Callback View
def google_callback(request):
    """Handles the response from Google and fetches recent emails using the Gmail API

    Args:
        request (_type_): _description_
    """
    flow = Flow.from_client_secrets_file(
        CLIENTS_SECRETS_FILE,
        scope = SCOPES,
        redirect_uri = REDIRECT_URI,
    )
    flow.fetch_token(authorization_response = request.built_absolute_uri())
    credentials = flow.credentials
    service = build('gmail', 'v1', credentials = credentials)
    
    result = service.users().messages().list(usersId = 'me', maxResults = 5).execute() 
    # Modify maxResult to change how many emails display
    messages = result.get('messages', [])
    
    email_data = []
    for msg in messages:
    # Structures each email display
        msg_detail = service.users().messages().get(userId = 'me', id = msg['id']).execute()
        headers = msg_detail['payload'].get('headers', [])
        subject = next((h['value'] for h in headers if h['name'] == 'Subject'), 'No Subject')   
        sender = next((h['value'] for h in headers if h['name'] == 'From'), "Unkown")
        snippet = msg_detail.get('snippet', '')
        email_data.append({
            'subject': subject,
            'sender': sender,
            'snippet': snippet,
        })
        
    return render(request, 'emails.html', {'emails': email_data})

# Next Step: 4. fetch_emails view

