import { useEffect, useState } from "react";

interface QueryResult<T> {
  loading: boolean;
  data?: T;
  error?: string;
}

/**
 * Custom hook for handling asynchronous GET requests to the backend. This provides observable
 * loading and error state to other components, allowing them to adapt their behaviour accordingly.
 * @param url A string containing the target URL for the GET request.
 * @param headers An optional object containing the headers for the GET request.
 * @returns A QueryResult object containing the retrieved data, along with the loading and error states.
 */
const useFetch = <T>(
  url: string,
  headers?: any,
): QueryResult<T> => {
  const [loading, setLoading] = useState<boolean>(true);
  const [data, setData] = useState<T | undefined>(undefined);
  const [error, setError] = useState<string | undefined>(undefined);

  useEffect(() => {
    fetch(url, {
      headers: headers,
    })
      .then((resp) => {
        if (!resp.ok) {
          throw "Backend Error: " + resp.status;
        }
        return resp.json();
      })
      .then((json) => setData(json as T))
      .catch((err) => {
        console.log(err);
        setError("Internal Server Error");
      })
      .finally(() => setLoading(false));
  }, [url, headers]);

  return { loading, data, error };
};

export default useFetch;
