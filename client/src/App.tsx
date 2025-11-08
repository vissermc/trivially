import {JSX, useState, useEffect} from 'react'
import './App.css'
import Header from './components/Header'
import AdminPanel from './components/AdminPanel'
import Quiz from './components/Quiz'
import Results from './components/Results'

interface Question {
  category: string;
  type: string;
  difficulty: string;
  question: string;
  options: string[];
}

interface Result {
  results: string[];
  score: number;
}

function App(): JSX.Element {
  const [questions, setQuestions] = useState<Question[]>([]);
  const [answers, setAnswers] = useState<string[]>([]);
  const [result, setResult] = useState<Result | null>(null);
  const [isAdmin, setIsAdmin] = useState<boolean>(false);

  useEffect(() => {
    const path = window.location.pathname;
    setIsAdmin(path === '/vj82fba8ifi1yht45d1mnd3q0ihf8x');

    fetch('/api/questions')
      .then(res => res.json())
      .then(data => {
        setQuestions(data);
        setAnswers(new Array(data.length).fill(''));
      });
  }, []);

  const handleAnswerChange = (questionIndex: number, answer: string) => {
    const newAnswers = [...answers];
    newAnswers[questionIndex] = answer;
    setAnswers(newAnswers);
  };

  const handleSubmit = () => {
    fetch('/api/checkanswers', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ answers })
    })
      .then(res => res.json())
      .then(data => setResult(data));
  };

  const handleRetry = () => {
    setResult(null);
    setAnswers(new Array(questions.length).fill(''));
  };

  const allAnswered = answers.every(answer => answer !== '');

  if (result) {
    return <Results result={result} questions={questions} answers={answers} onRetry={handleRetry} />;
  }

  return (
    <>
      <Header />
      <AdminPanel isAdmin={isAdmin} />
      <Quiz
        questions={questions}
        answers={answers}
        onAnswerChange={handleAnswerChange}
        onSubmit={handleSubmit}
        allAnswered={allAnswered}
      />
    </>
  )
}

export default App
