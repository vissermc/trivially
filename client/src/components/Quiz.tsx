import {JSX} from 'react'

interface Question {
  category: string
  type: string
  difficulty: string
  question: string
  options: string[]
}

interface QuizProps {
  questions: Question[]
  answers: string[]
  onAnswerChange: (questionIndex: number, answer: string) => void
  onSubmit: () => void
  allAnswered: boolean
}

function Quiz({questions, answers, onAnswerChange, onSubmit, allAnswered}: QuizProps): JSX.Element {
  return (
    <>
      <div className="questions-container">
        {questions.map((q, index) => (
          <div key={index} className="question">
            <h3>{q.question}</h3>
            <div className="options">
              {q.options.map((opt, i) => (
                <label key={i} className="option" onClick={() => onAnswerChange(index, opt)}>
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
        <button className="submit-btn" onClick={onSubmit} disabled={!allAnswered}>Submit Answers</button>
      </div>
    </>
  )
}

export default Quiz