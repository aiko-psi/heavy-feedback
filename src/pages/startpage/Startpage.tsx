import React, {useContext, useState} from 'react';
import styled from 'styled-components';


export default function Startpage() {
    return (
        <StartPageContainer>
            <Title>Heavy Feedback</Title>
            <Text>Willkommen!</Text>
            <Text>Heavy Feedback ist ein Auswertungstool für die Ergebnisse von EasyFeedback Umfragen.</Text>
        </StartPageContainer>
    )
}

const StartPageContainer = styled.main`
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`

const Title = styled.h1`
  font-family: "Gotham Medium";
  font-size: 72px;
`

const Text = styled.div`
  font-family: "Gotham Book";
  max-width: 80%;
  font-size: 25px;
  padding: 10px;
  text-align: center;
  line-height: 1.5em;
`
