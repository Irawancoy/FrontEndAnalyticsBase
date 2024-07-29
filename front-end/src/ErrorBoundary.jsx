import React from 'react';

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    this.logErrorToBackend(error, errorInfo);
  }

  logErrorToBackend(error, errorInfo) {
    fetch('http://localhost:8091/sample/logError', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        error: error.toString(),
        errorInfo: {
          componentStack: errorInfo.componentStack,
          message: error.message,
          stack: error.stack,
        }
      }),
    }).catch(err => console.error('Error logging to backend', err));
  }

  render() {
    if (this.state.hasError) {
      return <h1>Something went wrong.</h1>;
    }
    return this.props.children;
  }
}

export default ErrorBoundary;
