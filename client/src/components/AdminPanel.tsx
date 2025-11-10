import {JSX, useState, useEffect} from 'react'

interface AdminPanelProps {
    isAdmin: boolean
}

function AdminPanel({isAdmin}: AdminPanelProps): JSX.Element | null {
    const [newUrl, setNewUrl] = useState<string>('')

    const url = new URL(window.location.href);
    const segments = url.pathname.split('/').filter(Boolean);
    const resource = segments.pop();

        useEffect(() => {
            if (isAdmin) {
                fetch(`/api/${resource}`)
                    .then(res => res.text())
                    .then(url => setNewUrl(url))
            }
        }, [isAdmin])

    const handleUrlSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        fetch(`/api/${resource}`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({url: newUrl})
        })
            .then(res => {
                if (res.ok) {
                    alert('URL updated successfully')
                    window.location.reload()
                } else {
                    alert('Failed to update URL')
                }
            })
    }

    if (!isAdmin) return null

    return (
        <div className="admin-panel">
            <div className="admin-header">
                <h2>Admin Panel - Change Source URL</h2>
                <button className="close-admin-btn" onClick={() => window.location.href = '/'}>Close Admin</button>
            </div>
            <form onSubmit={handleUrlSubmit}>
                <input
                    type="url"
                    value={newUrl}
                    onChange={(e) => setNewUrl(e.target.value)}
                    placeholder="Enter new trivia source URL"
                    required
                />
                <button type="submit">Update URL</button>
            </form>
        </div>
    )
}

export default AdminPanel