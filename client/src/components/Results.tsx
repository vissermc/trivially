import {JSX} from 'react'

interface Question {
  category: string
  type: string
  difficulty: string
  question: string
  options: string[]
}

interface Result {
  results: string[]
  score: number
}

interface ResultsProps {
  result: Result
  questions: Question[]
  answers: string[]
  onRetry: () => void
}

function Results({result, questions, answers, onRetry}: ResultsProps): JSX.Element {
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
      <button className="retry-btn" onClick={onRetry}>Try Again</button>
    </div>
  )
}

export default Results