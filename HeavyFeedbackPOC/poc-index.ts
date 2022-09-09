import fetch from "node-fetch";

const INITIAL_URL = 'https://easy-feedback.de/tooltime/1487590/0vx4RN';

function convertToPreviewURL(initialUrl: string): string {
    let parts = initialUrl.split('/');
    parts[3] = 'vorschau';
    return parts.join('/');
}

async function getApiToken(url: string){
    const result = await fetch(url);
    const body = await result.text();
    const reg = /"apiMemberToken":\s*"([^"]*)"/;
    return reg.exec(body)[1];
}

async function getNextPage(token: string){
    const url = 'https://app.easy-feedback.com/api/pages/next';

    const fullInit = {
        headers: {
            'X-Api-Member-Token': token,
        },
    };
    const result = await fetch(url, fullInit);
    const data = await result.json();
    console.log('juhu')
}


//
const url = convertToPreviewURL(INITIAL_URL);
const token = await getApiToken(url);
getNextPage(token);

