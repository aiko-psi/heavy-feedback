import React, {useContext, useState} from 'react';
import logo from './logo.svg';
import {
    BrowserRouter,
    Routes,
    Route,
} from "react-router-dom";
import Startpage from './pages/startpage/Startpage';


type SurveyInfo = {
    suryeyId: number | null;
}

const SurveyContext = React.createContext<SurveyInfo>({
    suryeyId: null
})

export function useSurveyInfo() {
    return useContext(SurveyContext);
}

function App() {
    const [surveyId, setSurveyId] = useState<number | null>(null);
    
    return (
        <SurveyContext.Provider value={{suryeyId: surveyId}}>
            <BrowserRouter>
                <Routes>
                    <Route path={"/"} element={<Startpage/>}/>
                </Routes>
            </BrowserRouter>
        </SurveyContext.Provider>
    );
}

export default App;
