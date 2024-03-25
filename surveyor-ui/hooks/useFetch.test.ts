import { renderHook, waitFor } from "@testing-library/react";
import useFetch from "./useFetch";

const mockResponse = {
  field1: "value1",
  field2: "value2",
};

beforeEach(() => {
  jest
    .spyOn(global, "fetch")
    .mockImplementationOnce(
      jest.fn(() =>
        Promise.resolve({ json: () => Promise.resolve(mockResponse) }),
      ) as jest.Mock,
    );
});

test("should request data", async () => {
  const { result } = renderHook(() => useFetch<any>("http://test.url"));

  await waitFor(
    () => {
      expect(result.current.loading).toEqual(false);
    },
    { timeout: 5000 },
  );

  expect(result.current.data).toEqual(mockResponse);
});
