import {JSX, useState, useEffect} from 'react'
import './App.css'
import travelActiveLogo from './assets/travel_active.png'

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

  useEffect(() => {
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
    return (
      <div>
        <h2>Results</h2>
        <p>Score: {result.score}/{questions.length}</p>
        {questions.map((q, i) => (
          <div key={i}>
            <h3>{q.question}</h3>
            <p>Your answer: {answers[i]}</p>
            <p>Result: {result.results[i]}</p>
          </div>
        ))}
        <button className="retry-btn" onClick={handleRetry}>Try Again</button>
      </div>
    );
  }

  return (
    <>
      <div className="header">
        <img src={travelActiveLogo} alt="Travel Active Logo" className="logo" />
        <h1>Some fun before you go!</h1>
      </div>
      <div className="questions-container">
        {questions.map((q, index) => (
          <div key={index} className="question">
            <h3>{q.question}</h3>
            <div className="options">
              {q.options.map((opt, i) => (
                <label key={i} className="option" onClick={() => handleAnswerChange(index, opt)}>
                  <input
                    type="radio"
                    name={`question-${index}`}
                    value={opt}
                    checked={answers[index] === opt}
                    onChange={() => {}}
                  />
                  {opt}
                </label>
              ))}
            </div>
          </div>
        ))}
      </div>
      <div className="submit-container">
        <button className="submit-btn" onClick={handleSubmit} disabled={!allAnswered}>Submit Answers</button>
      </div>
    </>
  )
}

export default App
