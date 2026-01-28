export interface GitHubRepo {
  visibility: string;
  id: number;
  name: string;
  description: string | null;
  html_url: string;
  stargazers_count: number;
  forks_count: number;
  language: string | null;
  languages_url: string;
  license: {
    key: string;
    name: string;
  } | null;
  updated_at: string;
  fork: boolean;
  archived: boolean;
  languages?: Record<string, number>;
  topics?: string[];
}

export interface RepoLanguages {
  [key: string]: number;
}
