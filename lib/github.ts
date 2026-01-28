import { GitHubRepo, RepoLanguages } from '@/types';

const GITHUB_TOKEN = process.env.GITHUB_TOKEN;
const USERNAME = process.env.GITHUB_USERNAME || 'wakaranakattari';

export async function fetchRepositories(): Promise<GitHubRepo[]> {
  const url = `https://api.github.com/users/${USERNAME}/repos?sort=updated&per_page=100&type=owner`;

  const headers: Record<string, string> = {
    Accept: 'application/vnd.github.v3+json',
  };

  if (GITHUB_TOKEN) {
    headers['Authorization'] = `Bearer ${GITHUB_TOKEN}`;
  }

  try {
    const response = await fetch(url, {
      headers,
      next: { revalidate: 3600 },
    });

    if (!response.ok) {
      console.error(`GitHub API error: ${response.status}`);
      return [];
    }

    const repos: GitHubRepo[] = await response.json();
    return filterAndEnhanceRepos(repos);
  } catch (error) {
    console.error('Error fetching GitHub repos:', error);
    return [];
  }
}

async function filterAndEnhanceRepos(repos: GitHubRepo[]): Promise<GitHubRepo[]> {
  const filteredRepos = repos.filter((repo) => {
    if (repo.fork) return false;
    if (repo.archived) return false;
    if (repo.visibility === 'private') return false;
    if (repo.name === USERNAME) return false;
    if (repo.name.includes('.github.io')) return false;
    return true;
  });

  const reposWithData = await Promise.all(
    filteredRepos.slice(0, 12).map(async (repo) => {
      try {
        const languages = await fetchRepoLanguages(repo.languages_url);
        return {
          ...repo,
          languages,
          topics: repo.topics || [],
        };
      } catch {
        return { ...repo, languages: {}, topics: [] };
      }
    })
  );

  return reposWithData;
}

async function fetchRepoLanguages(url: string): Promise<RepoLanguages> {
  try {
    const response = await fetch(url, { next: { revalidate: 3600 } });
    if (!response.ok) return {};
    return response.json();
  } catch {
    return {};
  }
}
